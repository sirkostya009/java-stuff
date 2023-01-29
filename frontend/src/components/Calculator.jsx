import React, {Component} from "react";
import History from "./History.jsx";
import InputArea from "./InputArea.jsx";
import Numpad from "./Numpad.jsx";
import {connect} from "react-redux";
import {evaluate} from "../store/calculatorReducer/actions";
import {mapDispatchToProps, mapReduxStateToProps} from "../store/mappers";
import {evaluateExpression, join} from "../store/calculatorReducer/helpers";
import {Button} from "@material-ui/core";
import {fetchExpressions} from "../store/calculatorReducer/actions";

class Calculator extends Component {
  constructor(props) {
    super(props);
    this.state = {
      expressionValue: "",
    };

    this.onNumClick = this.onNumClick.bind(this);
    this.onOperatorClick = this.onOperatorClick.bind(this);
  }

  onNumClick(event) {
    const parsed = this.parseExpression();
    const buttonNumber = event.target.outerText;

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
    let parsed = this.parseExpression();
    const buttonOperator = event.target.outerText;

    if (parsed.length === 1 && buttonOperator !== "=") {
      parsed.push(buttonOperator);
    } else if (parsed.length === 2 && buttonOperator !== "=") {
      parsed[1] = buttonOperator;
    } else if (parsed.length === 3) {
      // warning: approaching shitty code segment
      const expression = join(parsed);
      this.props.dispatch(evaluate(expression));

      const evaluatedArray = evaluateExpression(expression);
      const result = this.resolveResult(evaluatedArray[4]);
      parsed = [result];

      if (buttonOperator !== "=" && result !== "") parsed.push(buttonOperator);
    }

    this.updateState(parsed);
  }

  resolveResult = (result) => isFinite(result) ? result : "";

  updateState(parsed) {
    this.setState({
      ...this.state,
      expressionValue: join(parsed),
    });
  }

  parseExpression(expression = this.state.expressionValue) {
    return expression.split(" ").filter((element) => element !== "");
  }

  render() {
    const {dispatch} = this.props;

    return (
      <div style={{display: "flex", flexDirection: "row"}}>
        <div style={{display: "flex", flexDirection: "column"}}>
          <InputArea value={this.state.expressionValue} />
          <Numpad onNumClick={this.onNumClick} onButtonClick={this.onOperatorClick} />
        </div>
        <History style={{width: 180}} />
        <Button variant="outlined" onClick={() => dispatch(fetchExpressions(5))} style={{width: 200, height: 100}}>
          QUERY THAT BE DOE
        </Button>
      </div>
    );
  }
}

export default connect(mapReduxStateToProps, mapDispatchToProps)(Calculator);
