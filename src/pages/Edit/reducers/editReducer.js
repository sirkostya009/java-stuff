import {FETCH_ENTITY, POST_ENTITY, PUT_ENTITY} from "../constants/actionTypes";

const initialState = {
  entity: null,
};

export default (state = initialState, {type, payload}) => {
  switch (type) {
    case PUT_ENTITY:
    case FETCH_ENTITY:
    case POST_ENTITY: return {
      ...state,
      entity: payload,
    };
    default: return state;
  }
};
