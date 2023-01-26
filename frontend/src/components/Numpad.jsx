import React, {Component} from "react";
import {Button} from "@material-ui/core";

class Numpad extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
        <div>
          <table>
            <tr>
              <th><Button variant="outlined">1</Button></th>
              <th><Button variant="outlined">2</Button></th>
              <th><Button variant="outlined">3</Button></th>
              <th><Button variant="outlined">/</Button></th>
            </tr>
            <tr>
              <th><Button variant="outlined">4</Button></th>
              <th><Button variant="outlined">5</Button></th>
              <th><Button variant="outlined">6</Button></th>
              <th><Button variant="outlined">*</Button></th>
            </tr>
            <tr>
              <th><Button variant="outlined">7</Button></th>
              <th><Button variant="outlined">8</Button></th>
              <th><Button variant="outlined">9</Button></th>
              <th><Button variant="outlined">+</Button></th>
            </tr>
            <tr>
              <th><Button variant="outlined">=</Button></th>
            </tr>
          </table>
        </div>
    );
  }
}

export default Numpad;
