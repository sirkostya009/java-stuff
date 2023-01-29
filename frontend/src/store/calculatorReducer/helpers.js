export const initialState = {
  history: [],
};

export const evaluateExpression = (expression) => {
  const {a, operator, b} = parseExpression(expression);

  const result = evaluate(a, operator, b);

  if (result === undefined) return undefined;

  return [a, operator, b, "=", result];

  function parseExpression(expression) {
    const expressionArray = expression.split(" ").filter((element) => element !== "");

    return {a: expressionArray[0], operator: expressionArray[1], b: expressionArray[2]};
  }

  function evaluate(a, operator, b) {
    switch (operator) {
      case "+":
        return parseInt(a) + parseInt(b);
      case "-":
        return parseInt(a) - parseInt(b);
      case "/":
        return parseInt(a) / parseInt(b);
      case "*":
        return parseInt(a) * parseInt(b);
      default:
        return undefined;
    }
  }
};

export const join = (expressionArray) => expressionArray?.join(" ");
