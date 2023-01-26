import React, {Component} from "react";
import {Button} from "@material-ui/core";

class Numpad extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const {
      onNumClick,
      onButtonClick
    } = this.props;

    return (
        <div>
          <table>
            <tr>
              <th><Button onClick={onNumClick} variant="outlined">1</Button></th>
              <th><Button onClick={onNumClick} variant="outlined">2</Button></th>
              <th><Button onClick={onNumClick} variant="outlined">3</Button></th>
              <th><Button onClick={onButtonClick} variant="outlined">/</Button></th>
            </tr>
            <tr>
              <th><Button onClick={onNumClick} variant="outlined">4</Button></th>
              <th><Button onClick={onNumClick} variant="outlined">5</Button></th>
              <th><Button onClick={onNumClick} variant="outlined">6</Button></th>
              <th><Button onClick={onButtonClick} variant="outlined">*</Button></th>
            </tr>
            <tr>
              <th><Button onClick={onNumClick} variant="outlined">7</Button></th>
              <th><Button onClick={onNumClick} variant="outlined">8</Button></th>
              <th><Button onClick={onNumClick} variant="outlined">9</Button></th>
              <th><Button onClick={onButtonClick} variant="outlined">-</Button></th>
            </tr>
            <tr>
              <th></th>
              <th><Button onClick={onNumClick} variant="outlined">0</Button></th>
              <th><Button onClick={onButtonClick} variant="outlined">=</Button></th>
              <th><Button onClick={onButtonClick} variant="outlined">+</Button></th>
            </tr>
          </table>
        </div>
    );
  }
}

export default Numpad;
