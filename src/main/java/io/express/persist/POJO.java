package io.express.persist;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import io.express.cache.CacheManager;

public class POJO implements Serializable {
    private static final long serialVersionUID = 1L;
    protected final static transient char OBJ_COUNT_CACHE_KEY = '#';
    private long _key_id;

    public long getId() {
        return _key_id;
    }

    public void setId(long id) {
        this._key_id = id;
    }

    protected String _this_table_name;

    public static void evictCache(String cache, Serializable key) {
        CacheManager.evict(cache, key);
    }

    public static void setCache(String cache, Serializable key, Serializable value) {
        CacheManager.set(cache, key, value);
    }

    public static Object getCache(String cache, Serializable key) {
        return CacheManager.get(cache, key);
    }

    /**
     * 分页列出所有对象
     *
     * @param page
     * @param size
     * @return
     */
    public List<? extends POJO> list(int page, int size) {
        String sql = "SELECT * FROM " + tableName() + " ORDER BY id DESC";
        return QueryHelper.query_slice(getClass(), sql, page, size);
    }

    public List<? extends POJO> filter(String filter, int page, int size) {
        String sql = "SELECT * FROM " + tableName() + " WHERE " + filter + " ORDER BY id DESC";
        return QueryHelper.query_slice(getClass(), sql, page, size);
    }

    public int totalCount(String filter) {
        return (int) QueryHelper.stat("SELECT COUNT(*) FROM " + tableName() + " WHERE " + filter);
    }

    protected String tableName() {
        if (_this_table_name == null)
            _this_table_name = "p_" + Inflector.getInstance().tableize(getClass());
        return _this_table_name;
    }

    /**
     * 返回对象对应的缓存区域名
     *
     * @return
     */
    public String cacheRegion() {
        return this.getClass().getSimpleName();
    }

    /**
     * 是否根据ID缓存对象，此方法对Get(long id)有效
     *
     * @return
     */
    protected boolean isObjectCachedByID() {
        return false;
    }

    /**
     * 插入对象到数据库表中
     *
     * @return
     */
    public long save() {
        if (getId() > 0)
            _insertObject(this);
        else
            setId(_insertObject(this));
        if (this.isObjectCachedByID())
            CacheManager.evict(cacheRegion(), OBJ_COUNT_CACHE_KEY);
        return getId();
    }

    /**
     * 根据id主键删除对象
     *
     * @return
     */
    public boolean delete() {
        boolean dr = evict(QueryHelper.update("DELETE FROM " + tableName() + " WHERE id=?", getId()) == 1);
        if (dr)
            CacheManager.evict(cacheRegion(), OBJ_COUNT_CACHE_KEY);
        return dr;
    }

    /**
     * 根据条件决定是否清除对象缓存
     *
     * @param er
     * @return
     */
    public boolean evict(boolean er) {
        if (er && isObjectCachedByID())
            CacheManager.evict(cacheRegion(), getId());
        return er;
    }

    /**
     * 清除指定主键的对象缓存
     *
     * @param obj_id
     */
    protected void evict(long obj_id) {
        CacheManager.evict(cacheRegion(), obj_id);
    }

    public POJO get(java.math.BigInteger id) {
        if (id == null)
            return null;
        return get(id.longValue());
    }

    /**
     * 根据主键读取对象详细资料，根据预设方法自动判别是否需要缓存
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends POJO> T get(long id) {
        if (id <= 0)
            return null;
        String sql = "SELECT * FROM " + tableName() + " WHERE id=?";
        boolean cached = isObjectCachedByID();
        return (T) QueryHelper.read_cache(getClass(), cached ? cacheRegion() : null, id, sql, id);
    }

    public List<? extends POJO> batchGet(List<Long> ids) {
        if (ids == null || ids.size() == 0)
            return null;
        StringBuilder sql = new StringBuilder("SELECT * FROM " + tableName() + " WHERE id IN (");
        for (int i = 1; i <= ids.size(); i++) {
            sql.append('?');
            if (i < ids.size())
                sql.append(',');
        }
        sql.append(')');
        List<? extends POJO> beans = QueryHelper.query(getClass(), sql.toString(), ids.toArray(new Object[ids.size()]));
        if (isObjectCachedByID()) {
            for (Object bean : beans) {
                CacheManager.set(cacheRegion(), ((POJO) bean).getId(), (Serializable) bean);
            }
        }
        return beans;
    }

    /**
     * 统计此对象的总记录数
     *
     * @return
     */
    public int totalCount() {
        if (this.isObjectCachedByID())
            return (int) QueryHelper.stat_cache(cacheRegion(), OBJ_COUNT_CACHE_KEY, "SELECT COUNT(*) FROM " + tableName());
        return (int) QueryHelper.stat("SELECT COUNT(*) FROM " + tableName());
    }

    /**
     * 批量加载项目
     *
     * @param pids
     * @return
     */
    public List<POJO> loadList(List<Long> p_pids) {
        if (p_pids == null)
            return null;
        final List<Long> pids = new ArrayList<Long>(p_pids.size());
        for (Number obj : p_pids) {
            pids.add(obj.longValue());
        }
        String cache = this.cacheRegion();
        List<POJO> prjs = new ArrayList<POJO>(pids.size()) {
            private static final long serialVersionUID = 1L;

            {
                for (int i = 0; i < pids.size(); i++)
                    add(null);
            }
        };
        List<Long> no_cache_ids = new ArrayList<Long>();
        for (int i = 0; i < pids.size(); i++) {
            long pid = pids.get(i);
            POJO obj = (POJO) CacheManager.get(cache, pid);

            if (obj != null)
                prjs.set(i, obj);
            else {
                no_cache_ids.add(pid);
            }
        }

        if (no_cache_ids.size() > 0) {
            List<? extends POJO> no_cache_prjs = batchGet(no_cache_ids);
            if (no_cache_prjs != null)
                for (POJO obj : no_cache_prjs) {
                    prjs.set(pids.indexOf(obj.getId()), obj);
                }
        }

        no_cache_ids = null;

        return prjs;
    }

    protected boolean isAutoLoadUser() {
        return false;
    }

    protected long getAutoLoadUser() {
        return 0L;
    }

    /**
     * 插入对象
     *
     * @param obj
     * @return 返回插入对象的主键
     */

    private static long _insertObject(POJO obj) {
        Map<String, String> pojo_bean = obj.listInsertableFields();
        String[] fields = pojo_bean.keySet().toArray(new String[pojo_bean.size()]);
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(obj.tableName());
        sql.append('(');
        for (int i = 0; i < fields.length; i++) {
            if (i > 0)
                sql.append(',');
            sql.append(fields[i]);
        }
        sql.append(") VALUES(");
        for (int i = 0; i < fields.length; i++) {
            if (i > 0)
                sql.append(',');
            sql.append('?');
        }
        sql.append(')');
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = QueryHelper.getConnection().prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < fields.length; i++) {
                ps.setObject(i + 1, pojo_bean.get(fields[i]));
            }
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getLong(1) : -1;
        } catch (SQLException e) {
            throw new DBException(e);
        } finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(ps);
            sql = null;
            fields = null;
            pojo_bean = null;
        }
    }

    /**
     * 列出要插入到数据库的域集合，子类可以覆盖此方法
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Map<String, String> listInsertableFields() {
        try {
            Map<String, String> props = BeanUtils.describe(this);
            if (getId() <= 0) {
                if (props.containsKey("id")) {
                    props.remove("id");
                }
                if (props.containsKey("nid")) {
                    props.remove("nid");
                }
            }
            props.remove("class");
            return props;
        } catch (Exception e) {
            throw new RuntimeException("Exception when Fetching fields of " + this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        // 不同的子类尽管ID是相同也是不相等的
        if (!getClass().equals(obj.getClass()))
            return false;
        POJO wb = (POJO) obj;
        return wb.getId() == getId();
    }
}
