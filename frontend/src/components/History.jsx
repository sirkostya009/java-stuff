import React, {Component} from "react";
import { connect } from "react-redux";

class History extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    console.log(this.props);

    return (
        <ul>{
          this.props.history?.map(expression =>
              <li>{expression}</li>)
        }</ul>
    );
  }
}

const mapDispatchToProps = (dispatch) => ({
  dispatch: dispatch,
});

const mapReduxStateToProps = (reduxState) => ({
  history: reduxState.history,
});

export default connect(mapReduxStateToProps, mapDispatchToProps)(History);
