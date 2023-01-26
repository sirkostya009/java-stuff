import { combineReducers } from "redux";
import { createStore } from "redux";
import { calculatorReducer } from "./calculatorReducer/reducer";

const rootReducer = combineReducers({
  calculator: calculatorReducer,
});

const store = createStore(rootReducer);

export default store;
