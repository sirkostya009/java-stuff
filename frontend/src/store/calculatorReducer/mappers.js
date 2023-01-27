export const mapReduxStateToProps = (reduxState) => ({
  history: reduxState.calculator.history,
});

export const mapDispatchToProps = (dispatch) => ({
  dispatch: dispatch,
});
