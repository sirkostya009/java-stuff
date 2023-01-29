import React, {Component} from "react";
import {Button} from "@material-ui/core";

class Numpad extends Component {
  render() {
    const {onNumClick, onButtonClick} = this.props;

    return (
      <table>
        <tbody>
          <tr>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                1
              </Button>
            </td>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                2
              </Button>
            </td>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                3
              </Button>
            </td>
            <td>
              <Button onClick={onButtonClick} variant="outlined">
                /
              </Button>
            </td>
          </tr>
          <tr>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                4
              </Button>
            </td>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                5
              </Button>
            </td>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                6
              </Button>
            </td>
            <td>
              <Button onClick={onButtonClick} variant="outlined">
                *
              </Button>
            </td>
          </tr>
          <tr>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                7
              </Button>
            </td>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                8
              </Button>
            </td>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                9
              </Button>
            </td>
            <td>
              <Button onClick={onButtonClick} variant="outlined">
                -
              </Button>
            </td>
          </tr>
          <tr>
            <td></td>
            <td>
              <Button onClick={onNumClick} variant="outlined">
                0
              </Button>
            </td>
            <td>
              <Button onClick={onButtonClick} variant="outlined">
                =
              </Button>
            </td>
            <td>
              <Button onClick={onButtonClick} variant="outlined">
                +
              </Button>
            </td>
          </tr>
        </tbody>
      </table>
    );
  }
}

export default Numpad;
