import React, {Component} from "react";
import History from "./History.jsx";
import InputArea from "./InputArea.jsx";
import Numpad from "./Numpad.jsx";

class Calculator extends Component {
  constructor(props) {
    super(props);
    this.state = {
      expressionValue: '',
    };

    this.onNumClick = this.onNumClick.bind(this);
    this.onOperatorClick = this.onOperatorClick.bind(this);
  }

  onNumClick(event) {
    const parsed = this.parseExpression()
    const buttonNumber = event.target.outerText;
    console.log(buttonNumber);

    if (parsed.length === 0) {
      parsed.push(buttonNumber);
    } else if (parsed.length === 1) {
      parsed[0] += buttonNumber;
    } else if (parsed.length === 2) {
      parsed.push(buttonNumber);
    } else if (parsed.length === 3) {
      parsed[2] += buttonNumber;
    }

    this.updateState(parsed);
  }

  onOperatorClick(event) {
    const parsed = this.parseExpression();
    const buttonOperator = event.target.outerText;
    console.log(buttonOperator);

    if (parsed.length === 1) {
      parsed.push(buttonOperator);
    } else if (parsed.length === 2 && buttonOperator !== '=') {
      parsed[1] = buttonOperator;
    } else if (parsed.length === 3) {
      // solve
    }

    this.updateState(parsed);
  }

  updateState(parsed) {
    console.log(parsed);
    this.setState({
      ...this.state,
      expressionValue: parsed.join(' '),
    })
  }

  parseExpression() {
    return this.state.expressionValue.split(' ')
      .filter((element) => element !== '')
  }

  render() {
    return (
      <div style={{display: "flex", flexDirection: "row"}}>
        <div style={{display: "flex", flexDirection: "column"}}>
          <InputArea value={this.state.expressionValue} />
          <Numpad onNumClick={this.onNumClick} onButtonClick={this.onOperatorClick} />
        </div>
        <History />
      </div>
    );
  }
}

export default Calculator;
