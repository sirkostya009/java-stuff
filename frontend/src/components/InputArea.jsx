import React, {Component} from "react";

class InputArea extends Component {
  render() {
    return <label>{this.props.value}</label>;
  }
}

export default InputArea;
