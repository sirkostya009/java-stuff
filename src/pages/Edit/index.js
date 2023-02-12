import {applyMiddleware, createStore} from "redux";
import thunkMiddleware from "redux-thunk";
import {Provider} from "react-redux";
import Edit from "./containers/Edit";
import reducer from "./reducers";

const store = createStore(reducer, applyMiddleware(thunkMiddleware))

export default (props) => (
    <Provider store={store}>
      <Edit {...props}/>
    </Provider>
);
