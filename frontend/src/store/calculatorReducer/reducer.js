import {initialState} from "./helpers";
import {CALCULATE_EXPRESSION, FETCH_EXPRESSIONS} from "./actionTypes";
import {evaluateExpression} from "./helpers";

export const calculatorReducer = (state = initialState, {type, payload}) => {
  switch (type) {
    case CALCULATE_EXPRESSION: {
      const result = evaluateExpression(payload);
      const newHistory = [...state.history];
      newHistory.unshift(result);

      return {
        ...state,
        history: newHistory,
      };
    }
    case FETCH_EXPRESSIONS: {
      const evaluatedExpressions = payload.map((expression) => evaluateExpression(expression));
      const newHistory = [...state.history];
      newHistory.unshift(...evaluatedExpressions);

      console.log(newHistory);
      return {
        ...state,
        history: newHistory,
      };
    }
    default:
      return state;
  }
};
