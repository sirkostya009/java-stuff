import React, {Component} from "react";
import { connect } from "react-redux";
import {mapReduxStateToProps} from "../store/calculatorReducer/mappers";

class History extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const copy = [...this.props.history];

    return (
        <ul>
          {copy[0] && <li key={0} style={{color: 'red'}}>{copy.shift()}</li>}
          {copy.map((expression, index) => <li key={index + 1}>{expression}</li>)}
        </ul>
    );
  }
}

export default connect(mapReduxStateToProps)(History);
