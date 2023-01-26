import React, {Component} from "react";

class InputForm extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <form onSubmit={this.props.handleSubmit}>
          <label>Enter an expression:
            <input type="text"
                   required={true}
                   placeholder={"Enter any binary expression..."}
                   value={this.props.value}
                   onChange={this.props.onChange}/>
          </label>
          <input type={'button'} value={"Calculate"} onClick={this.props.handleSubmit}/>
        </form>
    );
  }
}

export default InputForm;
