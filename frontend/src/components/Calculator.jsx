import React, {Component} from "react";
import History from "./History.jsx";
import Numpad from "./Numpad.jsx";
import {connect} from "react-redux";
import {evaluate} from "../store/calculatorReducer/actions";
import {mapDispatchToProps, mapReduxStateToProps} from "../store/mappers";
import {evaluateExpression, join} from "../store/calculatorReducer/helpers";
import {Button, InputLabel} from "@material-ui/core";
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
      copy = isFinite(evaluatedArray[4]) ? [evaluatedArray[4]] : [];

      if (buttonOperator !== "=" && copy.length) copy.push(buttonOperator);
    }

    this.updateState(copy);
  }

  updateState(newValue) {
    this.setState({
      ...this.state,
      expressionValue: newValue,
    });
  }

  render() {
    return (
      <div style={{display: "flex", flexDirection: "row", justifyContent: "center"}}>
        <div style={{display: "flex", flexDirection: "column"}}>
          <InputLabel style={{color: "black"}}>{join(this.state.expressionValue)}</InputLabel>
          <Numpad onNumClick={this.onNumClick} onOperatorClick={this.onOperatorClick} />
        </div>
        <History style={{minWidth: 180}} firstItemStyle={{color: "red"}} />
        <Button
          variant="outlined"
          onClick={() => this.props.dispatch(fetchExpressions(5))}
          style={{width: 200, height: 100}}
        >
          FETCH THAT BE DOE
        </Button>
      </div>
    );
  }
}

export default connect(mapReduxStateToProps, mapDispatchToProps)(Calculator);
