import {CALCULATE_EXPRESSION, FETCH_EXPRESSIONS} from "./actionTypes";

export const evaluate = (expression) => ({
  type: CALCULATE_EXPRESSION,
  payload: expression
})

export const fetchExpression = async (dispatch) => {
  fetch(`${process.env.REACT_APP_MATH_EXAMPLE_URL}4`)
    .then((array) => dispatch({ type: FETCH_EXPRESSIONS, payload: array }))
}
