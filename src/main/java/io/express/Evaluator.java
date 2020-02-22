package io.express;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 处理表达式
 *
 * @author Nixin
 */
public class Evaluator {
    public static long eval(String exp) {
        if (exp == null || exp.trim().length() == 0) {
            return 0L;
        }
        if (!exp.trim().endsWith("#")) {
            exp += "#";
        }
        List<String> list = split(exp);//转化成后缀表达式
        return doEval(list);// 真正求值
    }

    private static long doEval(List<String> list) {
        Stack<String> stack = new Stack<String>();
        String element;
        long n1, n2, result;
        try {
            for (int i = 0; i < list.size(); i++) {
                element = list.get(i);
                if (isOperator(element)) {
                    n1 = Long.parseLong(stack.pop());
                    n2 = Long.parseLong(stack.pop());
                    result = doOperate(n1, n2, element);
                    stack.push(Long.toString(result));
                } else {
                    stack.push(element);
                }
            }
            return Long.parseLong(stack.pop());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static long doOperate(long n1, long n2, String operator) {
        if (operator.equals("+"))
            return n1 + n2;
        else if (operator.equals("-"))
            return n1 - n2;
        else if (operator.equals("*"))
            return n1 * n2;
        else
            return n1 / n2;
    }

    private static boolean isOperator(String str) {
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
    }

    private static List<String> split(String exp) {// 将中缀表达式转化成为后缀表达式
        List<String> postExp = new ArrayList<String>();// 存放转化的后缀表达式的链表
        StringBuffer numBuffer = new StringBuffer();// 用来保存一个数的
        Stack<Character> opStack = new Stack<Character>();// 操作符栈
        char ch, preChar;
        opStack.push('#');
        try {
            for (int i = 0; i < exp.length(); ) {
                ch = exp.charAt(i);
                switch (ch) {
                    case '+':
                    case '-':
                    case '*':
                    case '/':
                        preChar = opStack.peek();
                        // 如果栈里面的操作符优先级比当前的大，则把栈中优先级大的都添加到后缀表达式列表中
                        while (priority(preChar) >= priority(ch)) {
                            postExp.add(String.valueOf(preChar));
                            opStack.pop();
                            preChar = opStack.peek();
                        }
                        opStack.push(ch);
                        i++;
                        break;
                    case '(':
                        // 左括号直接压栈
                        opStack.push(ch);
                        i++;
                        break;
                    case ')':
                        // 右括号则直接把栈中左括号前面的弹出，并加入后缀表达式链表中
                        char c = opStack.pop();
                        while (c != '(') {
                            postExp.add("" + c);
                            c = opStack.pop();
                        }
                        i++;
                        break;
                    // #号，代表表达式结束，可以直接把操作符栈中剩余的操作符全部弹出，并加入后缀表达式链表中
                    case '#':
                        char c1;
                        while (!opStack.isEmpty()) {
                            c1 = opStack.pop();
                            if (c1 != '#')
                                postExp.add(String.valueOf(c1));
                        }
                        i++;
                        break;
                    // 过滤空白符
                    case ' ':
                    case '\t':
                        i++;
                        break;
                    // 数字则凑成一个整数，加入后缀表达式链表中
                    default:
                        if (Character.isDigit(ch)) {
                            while (Character.isDigit(ch)) {
                                numBuffer.append(ch);
                                ch = exp.charAt(++i);
                            }
                            postExp.add(numBuffer.toString());
                            numBuffer = new StringBuffer();
                        }
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return postExp;
    }

    private static int priority(char op) {// 定义优先级
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '(':
            case '#':
                return 0;
        }
        return -1;
    }
}
