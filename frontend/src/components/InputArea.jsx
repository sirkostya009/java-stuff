import React, {Component} from "react";

class InputArea extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <label>{this.props.value}</label>
    );
  }
}

export default InputArea;
