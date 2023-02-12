import {FETCH_ENTITIES, REMOVE_ENTITY} from "../constants/actionTypes";

const initialState = {
  entities: []
};

export default (state = initialState, {type, payload}) => {
  switch (type) {
    case FETCH_ENTITIES: return {
      ...state,
      entities: payload,
    };
    case REMOVE_ENTITY: {
      const newEntities = state.entities.filter((entity) => entity.id !== payload);

      return {
        ...state,
        entities: newEntities,
      }
    }
    default: return state;
  }
};
