import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import Calculator from './components/Calculator.jsx';
import store from './store';

ReactDOM.render(
  <Provider store={store}>
    <Calculator />
  </Provider>,
  document.getElementById('root'),
);
