import React, {Component} from "react";
import {connect} from "react-redux";
import {mapReduxStateToProps} from "../store/mappers";
import {List, ListItem, ListItemText} from "@material-ui/core";

class History extends Component {
  render() {
    const copy = [...this.props.history];

    return (
      <List style={this.props.style} disablePadding>
        {copy[0] && (
          <ListItem key={0}>
            <ListItemText primary={copy.shift()} style={this.props.firstItemStyle} />
          </ListItem>
        )}
        {copy.map((expression, index) => (
          <ListItem key={index + 1}>
            <ListItemText primary={expression} />
          </ListItem>
        ))}
      </List>
    );
  }
}

export default connect(mapReduxStateToProps)(History);
