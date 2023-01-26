import React, {Component} from "react";

class History extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <ul>{
          this.props.values.map(expression =>
              <li>{expression}</li>)
        }</ul>
    );
  }
}

export default History;
