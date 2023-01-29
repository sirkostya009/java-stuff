import {CALCULATE_EXPRESSION, FETCH_EXPRESSIONS} from "./actionTypes";

export const evaluate = (expression) => ({
  type: CALCULATE_EXPRESSION,
  payload: expression,
});

const MathExamplesUrl = "http://localhost:8080/math/example?count=";

export const fetchExpressions = (count) => (dispatch) => {
  console.log("Fetching expressions...");
  fetch(`${MathExamplesUrl}${count}`)
    .then((response) => response.json())
    .then((json) => {
      console.log(json);
      dispatch({
        type: FETCH_EXPRESSIONS,
        payload: json,
      });
    });
};
