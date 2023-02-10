import {applyMiddleware, createStore} from "redux";
import reducer from "./reducers";
import thunkMiddleware from "redux-thunk";
import {Provider} from "react-redux";
import Entities from "./containers/Entities";

const store = createStore(reducer, applyMiddleware(thunkMiddleware));

export default (props) => (
    <Provider store={store}>
      <Entities {...props} />
    </Provider>
)
