package clases;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tony
 */
public class Node {

    public String val;
    public Node left, right;
    public boolean isOperator, isFunction;

    public Node(String val) {
        this.val = val;
        left = null;
        right = null;
        isOperator = false;
        isFunction = false;
    }

    public Node(String val, boolean isOperator) {
        this.val = val;
        left = null;
        right = null;
        this.isOperator = isOperator;
        isFunction = false;
    }

    public Node(String val, boolean isOperator, boolean isFunction) {
        this.val = val;
        left = null;
        right = null;
        this.isOperator = isOperator;
        this.isFunction = isFunction;
    }
}
