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
      expressionValue: [],
    };

    this.onNumClick = this.onNumClick.bind(this);
    this.onOperatorClick = this.onOperatorClick.bind(this);
  }

  onNumClick(event) {
    const copy = [...this.state.expressionValue];
    const buttonNumber = event.target.outerText;

    if (copy.length === 0) {
      copy.push(buttonNumber);
    } else if (copy.length === 1) {
      copy[0] += buttonNumber;
    } else if (copy.length === 2) {
      copy.push(buttonNumber);
    } else if (copy.length === 3) {
      copy[2] += buttonNumber;
    }

    this.updateState(copy);
  }

  onOperatorClick(event) {
    let copy = [...this.state.expressionValue];
    const buttonOperator = event.target.outerText;

    if (copy.length === 1 && buttonOperator !== "=") {
      copy.push(buttonOperator);
    } else if (copy.length === 2 && buttonOperator !== "=") {
      copy[1] = buttonOperator;
    } else if (copy.length === 3) {
      const expression = join(copy);
      this.props.dispatch(evaluate(expression));

      const evaluatedArray = evaluateExpression(expression);
      const result = this.resolveResult(evaluatedArray[4]);
      copy = [result];

      if (buttonOperator !== "=" && result !== "") copy.push(buttonOperator);
    }

    this.updateState(copy);
  }

  resolveResult(result) {
    return isFinite(result) ? result : "";
  }

  updateState(newValue) {
    this.setState({
      ...this.state,
      expressionValue: newValue,
    });
  }

  render() {
    const {dispatch} = this.props;

    return (
      <div style={{display: "flex", flexDirection: "row"}}>
        <div style={{display: "flex", flexDirection: "column"}}>
          <InputArea value={join(this.state.expressionValue)} />
          <Numpad onNumClick={this.onNumClick} onButtonClick={this.onOperatorClick} />
        </div>
        <History style={{width: 180}} />
        <Button variant="outlined" onClick={() => dispatch(fetchExpressions(5))} style={{width: 200, height: 100}}>
          FETCH THAT BE DOE
        </Button>
      </div>
    );
  }
}

export default connect(mapReduxStateToProps, mapDispatchToProps)(Calculator);
