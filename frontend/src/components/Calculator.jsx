import React, {Component} from "react";
import History from "./History.jsx";
import InputForm from "./InputForm.jsx";
import Numpad from "./Numpad.jsx";

class Calculator extends Component {
  constructor(props) {
    super(props);
    this.state = {
      expressionValue: '',
      values: []
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.onWrite = this.onWrite.bind(this);
  }

  evaluate(a, operator, b) {
    switch (operator) {
      case '+': return parseInt(a) + parseInt(b);
      case '-': return parseInt(a) - parseInt(b);
      case '/': return parseInt(a) / parseInt(b);
      case '*': return parseInt(a) * parseInt(b);
      default:  return undefined;
    }
  }

  evaluateExpression(expression) {
    const expressionArray = expression.split(' ').filter(element => element !== '');
    console.log(expressionArray);

    const a = expressionArray[0];
    const operator = expressionArray[1];
    const b = expressionArray[2];

    const result = this.evaluate(a, operator, b);

    if (!result) return;

    const representation = `${expressionArray.join(' ')} = ${result}`;

    console.log(representation);

    const newValues = [...this.state.values];
    newValues.unshift(representation);
    this.setState({values: newValues, expressionValue: result})
  }

  handleSubmit(event) {
    event.preventDefault();
    console.log(event.target[0]);
    this.evaluateExpression(event.target[0].value);
  }

  onWrite(event) {
    console.log(event);
    this.setState({expressionValue: event.target.value})
  }

  render() {
    return (
      <div>
        <InputForm value={this.state.expressionValue} handleSubmit={this.handleSubmit} onChange={this.onWrite} />
        <History values={this.state.values} />
        <Numpad />
      </div>
    );
  }
}

export default Calculator;
