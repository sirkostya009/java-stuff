import {applyMiddleware, combineReducers} from "redux";
import { createStore } from "redux";
import { calculatorReducer } from "./calculatorReducer/reducer";
import thunk from "redux-thunk";

const rootReducer = combineReducers({
  calculator: calculatorReducer,
});

const store = createStore(rootReducer, applyMiddleware(thunk));

export default store;
