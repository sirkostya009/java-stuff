import {applyMiddleware, combineReducers} from "redux";
import { createStore } from "redux";
import { calculatorReducer } from "./calculatorReducer/reducer";
import thunkMiddleware from "redux-thunk";

const rootReducer = combineReducers({
  calculator: calculatorReducer,
});

const store = createStore(rootReducer, applyMiddleware(thunkMiddleware));

export default store;
