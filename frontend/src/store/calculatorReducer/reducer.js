import {initialState} from "./helpers";
import {CALCULATE_EXPRESSION, FETCH_EXPRESSIONS} from "./actionTypes";
import {evaluateExpression, join} from "./helpers";

export const calculatorReducer = (state = initialState, {type, payload}) => {
  switch (type) {
    case CALCULATE_EXPRESSION: {
      const result = join(evaluateExpression(payload));
      const newHistory = [...state.history];
      newHistory.unshift(result);

      return {
        ...state,
        history: newHistory,
      };
    }
    case FETCH_EXPRESSIONS: {
      const evaluatedExpressions = payload.map((expression) => join(evaluateExpression(expression)));
      const newHistory = [...state.history];
      newHistory.unshift(...evaluatedExpressions);

      return {
        ...state,
        history: newHistory,
      };
    }
    default:
      return state;
  }
};
