export const mapReduxStateToProps = (reduxState) => ({
  ...reduxState.calculator,
});

export const mapDispatchToProps = (dispatch) => ({
  dispatch,
});
