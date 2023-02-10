import {FETCH_ENTITIES, REMOVE_ENTITY} from "../constants/actionTypes";
import {deleteId, getJson} from "../../../requests";
import config from "../../../config";

const {
  BASE_URL,
  ENTITY_SERVICE
} = config;

export const fetchEntities = () => (dispatch) => {
  getJson({url: `${BASE_URL}${ENTITY_SERVICE}`})
    .then((json) => {
      dispatch({
        type: FETCH_ENTITIES,
        payload: json,
      });
    })
    .catch(() => {
      dispatch({
        type: FETCH_ENTITIES,
        payload: [
          { id: 1, name: 'cool' },
          { id: 3, name: 'cool' },
          { id: 4, name: 'cool' },
          { id: 5, name: 'cool' },
          { id: 6, name: 'cool' },
          { id: 2, name: 'cool' },
        ],
      });
    });
};

export const deleteEntity = (id) => (dispatch) => {
  console.log("delete called");
  deleteId(`${BASE_URL}${ENTITY_SERVICE}/${id}`)
    .then(() => {
      dispatch({
        type: REMOVE_ENTITY,
        payload: id,
      });
    });
};
