import java.util.*;
import java.util.regex.Pattern;

public class Expression {
    private enum Operation {
        ADD, SUBTRACT,
        MULTIPLY, DIVIDE, MODULO,
        EXPONENTIATION, VARIABLE, CONSTANT,
        SINE, COSINE, TANGENT, COTANGENT, SECANT, COSECANT
    }

    private final List<Operation> TRIG_FUNCTIONS = new ArrayList<>() {{
        add(Operation.SINE);
        add(Operation.COSINE);
        add(Operation.TANGENT);
        add(Operation.COSECANT);
        add(Operation.SECANT);
        add(Operation.COTANGENT);
    }};

    private static final Pattern CONSTANT_PATTERN = Pattern.compile("\\d*\\.?\\d*");

    private Expression left_term;
    private Expression right_term;

    private Operation operation;

    private double constant_value;

    private String variable;

    public Expression(String expression) {
        expression = expression.toLowerCase();
        expression = expression.replaceAll(" ", "");
        expression = removeExtraParentheses(expression);

        if (expression.length() > 0 && CONSTANT_PATTERN.matcher(expression).matches()) {
            constant_value = Double.parseDouble(expression);
            operation = Operation.CONSTANT;
        } else if ("asdfghjklqwertyuiopzxcvbnm".contains(expression) && expression.length() == 1) {
            operation = Operation.VARIABLE;
            variable = expression;
        } else if (checkParentheses(expression)) {
            String parenContents = expression.substring(expression.indexOf("(") + 1, expression.length() - 1);
            if (expression.startsWith("sin(") || expression.startsWith("sine(")) {
                operation = Operation.SINE;
                left_term = new Expression(parenContents);
            } else if (expression.startsWith("cos(") || expression.startsWith("cosine(")) {
                operation = Operation.COSINE;
                left_term = new Expression(parenContents);
            } else if (expression.startsWith("tan(") || expression.startsWith("tangent(")) {
                operation = Operation.TANGENT;
                left_term = new Expression(parenContents);
            } else if (expression.startsWith("cot(") || expression.startsWith("cotangent(")) {
                operation = Operation.COTANGENT;
                left_term = new Expression(parenContents);
            } else if (expression.startsWith("sec(") || expression.startsWith("secant(")) {
                operation = Operation.SECANT;
                left_term = new Expression(parenContents);
            } else if (expression.startsWith("csc(") || expression.startsWith("cosecant(")) {
                operation = Operation.COSECANT;
                left_term = new Expression(parenContents);
            }
        }

        boolean plus = false, minus = false, mult = false, div = false, exp = false, mod = false;
        int plusIndex = 0, minusIndex = 0, multIndex = 0, divIndex = 0, modIndex = 0;

        int depth = 0;

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            depth += switch (c) {
                case '(' -> 1;
                case ')' -> -1;
                default -> 0;
            };

            if (depth == 0) {
                switch (c) {
                    case '+' -> {
                        plus = true;
                        plusIndex = i;
                    } case '-' -> {
                        minus = true;
                        minusIndex = i;
                    } case '*' -> {
                        mult = true;
                        multIndex = i;
                    } case '/' -> {
                        div = true;
                        divIndex = i;
                    } case '%' -> {
                        mod = true;
                        modIndex = i;
                    } case '^' -> exp = true;
                }
            }
        }

        if (plus && minus) {
            if (plusIndex < minusIndex) {
                splitExpression(expression, "+");
            } else {
                splitExpression(expression, "-");
            }
        } else if (plus) {
            splitExpression(expression,"+");
        } else if (minus) {
            splitExpression(expression, "-");
        }

        else if (mult && div && mod) {
            if (multIndex < divIndex && multIndex < modIndex) {
                splitExpression(expression, "*");
            } else if (divIndex < multIndex && divIndex < modIndex) {
                splitExpression(expression, "/");
            } else {
                splitExpression(expression, "%");
            }
        } else if (mult && div) {
            if (multIndex < divIndex) {
                splitExpression(expression, "*");
            } else {
                splitExpression(expression, "/");
            }
        } else if (mult && mod) {
            if (multIndex < modIndex) {
                splitExpression(expression, "*");
            } else {
                splitExpression(expression, "%");
            }
        } else if (div && mod) {
            if (divIndex < modIndex) {
                splitExpression(expression, "/");
            } else {
                splitExpression(expression, "%");
            }
        } else if (mult) {
            splitExpression(expression,"*");
        } else if (div) {
            splitExpression(expression, "/");
        } else if (mod) {
            splitExpression(expression, "%");
        }

        else if (exp) {
            splitExpression(expression, "^");
        }
    }

    private void splitExpression(String expression, String operator) {
        switch (operator) {
            case "+" -> operation = Operation.ADD;
            case "-" -> operation = Operation.SUBTRACT;
            case "*" -> operation = Operation.MULTIPLY;
            case "/" -> operation = Operation.DIVIDE;
            case "^" -> operation = Operation.EXPONENTIATION;
            case "%" -> operation = Operation.MODULO;
        }

        left_term = new Expression(expression.substring(0, expression.indexOf(operator)));
        right_term = new Expression(expression.substring(expression.indexOf(operator)+1));
    }

    public double evaluate(Map<String, Double> variables) {
        return switch (operation) {
            case CONSTANT -> constant_value;
            case VARIABLE -> variables.get(variable);
            case ADD -> left_term.evaluate(variables) + right_term.evaluate(variables);
            case SUBTRACT -> left_term.evaluate(variables) - right_term.evaluate(variables);
            case DIVIDE -> left_term.evaluate(variables) / right_term.evaluate(variables);
            case MULTIPLY -> left_term.evaluate(variables) * right_term.evaluate(variables);
            case EXPONENTIATION -> Math.pow(left_term.evaluate(variables), right_term.evaluate(variables));
            case MODULO -> left_term.evaluate(variables) % right_term.evaluate(variables);
            case SINE -> Math.sin(left_term.evaluate(variables));
            case COSINE -> Math.cos(left_term.evaluate(variables));
            case TANGENT -> Math.tan(left_term.evaluate(variables));
            case COTANGENT -> 1 / Math.tan(left_term.evaluate(variables));
            case SECANT -> 1 / Math.cos(left_term.evaluate(variables));
            case COSECANT -> 1 / Math.sin(left_term.evaluate(variables));
        };
    }

    public double evaluate() {
        return switch (operation) {
            case CONSTANT -> constant_value;
            case ADD -> left_term.evaluate() + right_term.evaluate();
            case SUBTRACT -> left_term.evaluate() - right_term.evaluate();
            case DIVIDE -> left_term.evaluate() / right_term.evaluate();
            case MULTIPLY -> left_term.evaluate() * right_term.evaluate();
            case EXPONENTIATION -> Math.pow(left_term.evaluate(), right_term.evaluate());
            case MODULO -> left_term.evaluate() % right_term.evaluate();
            case SINE -> Math.sin(left_term.evaluate());
            case COSINE -> Math.cos(left_term.evaluate());
            case TANGENT -> Math.tan(left_term.evaluate());
            default -> throw new IllegalStateException("Unexpected value: " + operation + ". This Calculator.Expression requires the a value for variable '" + variable + "'.");
        };
    }

    private static String removeExtraParentheses(String expression) {
        if (expression.startsWith("(") && expression.endsWith(")")) {
            int depth = 0;
            for (int i = 0; i < expression.length() - 1; i++) {
                depth += switch (expression.charAt(i)) {
                    case '(' -> 1;
                    case ')' -> -1;
                    default -> 0;
                };
                if (depth == 0) {
                    return expression;
                }
            }

            return expression.substring(1, expression.length() - 1);
        }

        return expression;
    }

    private static boolean checkParentheses(String expression) {
        int depth = 0;
        boolean entered = false;
        for (int i = 0; i < expression.length() - 1; i++) {
            depth += switch (expression.charAt(i)) {
                case '(' -> 1;
                case ')' -> -1;
                default -> 0;
            };

            if (depth == 1) {
                entered = true;
            }
            if (depth == 0 && entered) {
                return false;
            }
        }
        return true;
    }

    public Set<String> getVariables() {
        Set<String> variables = new HashSet<>();
        if (operation.equals(Operation.VARIABLE)) {
            variables.add(variable);
        } else if (TRIG_FUNCTIONS.contains(operation)) {
            variables.addAll(left_term.getVariables());
        } else if (!operation.equals(Operation.CONSTANT)) {
            variables.addAll(left_term.getVariables());
            variables.addAll(right_term.getVariables());
        }
        return variables;
    }

    @Override
    public String toString() {
        return switch (operation) {
            case VARIABLE -> variable;
            case CONSTANT -> constant_value % 1 == 0 ? Integer.toString((int) constant_value): Double.toString(constant_value);
            case ADD -> left_term.toString() + " + " + right_term.toString();
            case SUBTRACT -> left_term.toString() + " - " + right_term.toString();
            case DIVIDE -> wrapTerm(left_term) + " / " + wrapTerm(right_term);
            case MULTIPLY -> wrapTerm(left_term) + " * " + wrapTerm(right_term);
            case EXPONENTIATION -> wrapTerm(left_term) + " ^ " + wrapTerm(right_term);
            case MODULO -> wrapTerm(left_term) + " % " + wrapTerm(right_term);
            case SINE -> "sin(" + left_term.toString() + ")";
            case COSINE -> "cos(" + left_term.toString() + ")";
            case TANGENT -> "tan(" + left_term.toString() + ")";
            case COTANGENT -> "cot(" + left_term.toString() + ")";
            case COSECANT -> "csc(" + left_term.toString() + ")";
            case SECANT -> "sec(" + left_term.toString() + ")";
        };
    }

    private static String wrapTerm(Expression term) {
        String string = term.toString();
        if (term.operation != Operation.CONSTANT && term.operation != Operation.VARIABLE) {
            string = "(" + string + ")";
        }
        return string;
    }

    public static void main(String[] args) {
        Expression expression = new Expression("1 + cot(1 + c + a + b + x + a + x)");
        Map<String, Double> variables = new HashMap<>();
        variables.put("x", 10d);
        System.out.println(expression);
        System.out.println(expression.getVariables());
    }
}
