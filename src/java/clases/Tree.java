package clases;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author tony
 */
import java.util.Stack;
import java.util.Vector;

public class Tree {

    private Node root;

    public Tree() {
        root = null;
    }

    public Tree(Node root) {
        this.root = root;
    }

    public Tree(String exp) {
        root = null;
        Vector<String> infix = exp2vector(exp);
        Vector<String> prefix = infix2prefix(infix);
        build(prefix);
    }

    public boolean isNumber(char c) {
        return (c >= '0' && c <= '9');
    }

    public boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isNumber(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean isBracket(char c) {
        return (c == '(' || c == ')' || c == '{' || c == '}');
    }

    public char getBracket(char c) {
        if (c == '(') {
            return '(';
        }
        if (c == ')') {
            return ')';
        }
        if (c == '{') {
            return '(';
        }
        if (c == '}') {
            return ')';
        }
        return ' ';
    }

    public boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^');
    }

    public boolean isFunction(String s) {
        return (s.equals("sin") || s.equals("cos") || s.equals("tan") || s.equals("sec") || s.equals("csc") || s.equals("cot") || s.equals("ln") || s.equals("log") || s.equals("sqrt") || s.equals("frac"));
    }

    public boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z');
    }

    public boolean isAlpha(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!isAlpha(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public int prioridad(char op) {
        if (op == '^') {
            return 3;
        }
        if (op == '*' || op == '/') {
            return 2;
        }
        if (op == '+' || op == '-') {
            return 1;
        }
        return 0;
    }

    public Vector<String> exp2vector(String exp) {
        exp += "$$";
        Vector<String> ans = new Vector<String>();

        for (int i = 0; i < exp.length(); i++) {
            char curr = exp.charAt(i);
            if (curr == ' ' || curr == '\\') {
                continue;
            }
            if (isBracket(curr)) {
                ans.add(getBracket(curr) + "");
            } else if (isOperator(curr)) {
                ans.add(curr + "");
            } else if (isNumber(curr)) {
                String num = "";
                while (i < exp.length() && isNumber(exp.charAt(i)) && exp.charAt(i) != '\\') {
                    num += exp.charAt(i);
                    i++;
                    curr = exp.charAt(i);
                }
                ans.add(num);
                i--;
            } else if (isAlpha(curr)) {
                String func = "";
                while (i < exp.length() && isAlpha(exp.charAt(i)) && exp.charAt(i) != '\\') {
                    func += exp.charAt(i);
                    i++;
                    curr = exp.charAt(i);
                }
                ans.add(func);
                i--;
            }
        }
        for (int i = 0; i < ans.size(); i++) {
            if (ans.get(i).equals("cdot")) {
                ans.set(i, "*");
            }
            if (ans.get(i).equals("left") || ans.get(i).equals("right")) {
                ans.remove(i);
            }
        }

        return ans;
    }

    public Vector<String> infix2prefix(Vector<String> infix) {
        Vector<String> prefix = new Vector<String>();
        Stack<Character> operators = new Stack<Character>();
        for (int i = infix.size() - 1; i >= 0; i--) {
            String curr = infix.get(i);
            if (isNumber(curr) || isFunction(curr) || isAlpha(curr)) {
                prefix.add(curr);
            } else if (curr.equals(")")) {
                operators.push(curr.charAt(0));
            } else if (curr.equals("(")) {
                while (!operators.empty() && operators.peek() != ')') {
                    prefix.add(operators.pop() + "");
                }
                operators.pop();
            } else if (isOperator(curr.charAt(0))) {
                while (!operators.empty() && prioridad(operators.peek()) > prioridad(curr.charAt(0))) {
                    prefix.add(operators.pop() + "");
                }
                operators.push(curr.charAt(0));
            }

        }
        while (!operators.empty()) {
            prefix.add(operators.pop() + "");
        }

        Vector<String> ans = new Vector<String>();
        for (int i = prefix.size() - 1; i >= 0; i--) {
            ans.add(prefix.get(i));
        }

        return ans;
    }

    public void build(Vector<String> prefix) {
        Vector<Node> nodos = new Vector<Node>();
        for (int i = 0; i < prefix.size(); i++) {
            Node aux = new Node(prefix.get(i), isOperator(prefix.get(i).charAt(0)), isFunction(prefix.get(i)));
            nodos.add(aux);
        }
        Stack<Node> pila = new Stack<Node>();
        for (int i = prefix.size() - 1; i >= 0; i--) {
            if (nodos.get(i).isOperator) {
                nodos.get(i).left = pila.pop();
                nodos.get(i).right = pila.pop();
                pila.push(nodos.get(i));

            } else if (nodos.get(i).isFunction) {
                if (nodos.get(i).val.equals("frac")) {
                    nodos.get(i).left = pila.pop();
                    nodos.get(i).right = pila.pop();
                } else {
                    nodos.get(i).right = pila.pop();
                }
                pila.push(nodos.get(i));
            } else {
                pila.push(nodos.get(i));
            }
        }
        root = pila.pop();
    }

    public String inOrder(Node node) {
        String ans = "";
        if (node == null) {
            return ans;
        }
        if (node.isOperator) {
            ans += "(";
        }
        ans += inOrder(node.left);
        ans += node.val;
        ans += inOrder(node.right);
        if (node.isOperator) {
            ans += ")";
        }
        return ans;
    }

    public String inOrder() {
        return inOrder(root);
    }

    public String getVal(Node node) {
        String ans = "";
        if (node == null) {
            return ans;
        }
        if (node.isOperator) {
            if (node.val.equals("/")) {
                ans += "\\frac{";
                ans += getVal(node.left);
                ans += "}{";
                ans += getVal(node.right);
                ans += "}";
                return ans;
            } else if (node.val.equals("^")) {
                ans += "\\left(";
                ans += getVal(node.left);
                ans += node.val + "{";
                ans += getVal(node.right);
                ans += "}\\right)";
                return ans;
            }

            ans += "\\left(";
            ans += getVal(node.left);
            ans += node.val;
            ans += getVal(node.right);
            ans += "\\right)";
            return ans;

        }
        if (node.isFunction) {
            if (node.val.equals("sqrt")) {
                ans += "\\sqrt{";
                ans += getVal(node.right);
                ans += "}";

                return ans;
            }
            if (node.val.equals("frac")) {
                ans += "\\frac{";
                ans += getVal(node.left);
                ans += "}{";
                ans += getVal(node.right);
                ans += "}";
                return ans;
            }
            ans += node.val;
            ans += "\\left(";
            ans += getVal(node.right);
            ans += "\\right)";
            return ans;
        }
        return node.val;
    }

    public String derivate(Node node) {
        if (node == null) {
            return "";
        }
        if (node.isOperator || node.isFunction) {
//            return getVal(node);
            switch (node.val) {
                case "+", "-" -> {
                    return "\\left(" + derivate(node.left) + node.val + derivate(node.right) + "\\right)";
                }
                case "*" -> {
                    return "\\left(" + derivate(node.left) + "\\cdot " + getVal(node.right) + "+" + getVal(node.left) + "\\cdot " + derivate(node.right) + "\\right)";
                }
                case "/", "frac" -> {
                    return "\\left(\\frac{" + derivate(node.left) + "\\cdot " + getVal(node.right) + "-" + getVal(node.left) + "\\cdot " + derivate(node.right) + "}{\\left(" + getVal(node.right) + "\\right)^2}\\right)";
                }
                case "^" -> {
                    return "\\left(" + getVal(node.right) + "\\cdot " + getVal(node.left) + "^{" + getVal(node.right) + "-1}" + "\\cdot " + derivate(node.left) + "+" + getVal(node) + "\\cdot " + "ln\\left(" + getVal(node.left) + "\\right)" + "\\cdot" + derivate(node.right) + " \\right)";
                }
                case "sin" -> {
                    return "\\left(\\cos\\left(" + getVal(node.right) + "\\right)\\cdot " + derivate(node.right) + "\\right)";
                }
                case "cos" -> {
                    return "\\left(-\\sin\\left(" + getVal(node.right) + "\\right)\\cdot " + derivate(node.right) + "\\right)";
                }
                case "tan" -> {
                    return "\\left(\\sec\\left(" + getVal(node.right) + "\\right)^2\\cdot " + derivate(node.right) + "\\right)";
                }
                case "sec" -> {
                    return "\\left(\\sec\\left(" + getVal(node.right) + "\\right)\\cdot \\tan\\left(" + getVal(node.right) + "\\right)\\cdot " + derivate(node.right) + "\\right)";
                }
                case "csc" -> {
                    return "\\left(-\\csc\\left(" + getVal(node.right) + "\\right)\\cdot cot\\left((" + getVal(node.right) + "\\right)\\cdot " + derivate(node.right) + "\\right)";
                }
                case "cot" -> {
                    return "\\left(-\\csc\\left(" + getVal(node.right) + "\\right)^2\\cdot " + derivate(node.right) + "\\right)";
                }
                case "ln" -> {
                    return "\\left( \\frac{1}{" + getVal(node.right) + "}\\cdot " + derivate(node.right) + "\\right)";
                }
                case "log" -> {
                    return "\\left(\\frac{1}{" + getVal(node.right) + "}\\cdot ln(10))\\cdot " + derivate(node.right) + "\\right)";
                }
                case "sqrt" -> {
                    return "\\left( \\frac{1}{2\\cdot \\sqrt{ " + getVal(node.right) + "}} \\cdot " + derivate(node.right) + "\\right)";
                }
                default -> {
                }
            }
        } else {
            if (node.val.equals("x")) {
                return "1";
            } else {
                return "0";
            }
        }
        return "";
    }

    public String derivate() {
        return derivate(root);
    }

}
