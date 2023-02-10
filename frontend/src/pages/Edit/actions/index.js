import {getJson, postJson, putJson} from "requests";
import {CLEAR_ENTITY, FETCH_ENTITY, POST_ENTITY, PUT_ENTITY} from "../constants/actionTypes";
import config from "config";

const {
  BASE_URL,
  ENTITY_SERVICE
} = config;

const url = `${BASE_URL}${ENTITY_SERVICE}`;

export const fetchEntity = (id) => (dispatch) => {
  getJson({url: `${url}/${id}`})
    .then((json) => {
      dispatch({
        type: FETCH_ENTITY,
        payload: json,
      });
    })
    .catch(() => {
      dispatch({
        type: FETCH_ENTITY,
        payload: { id, name: 'nigger' }
      });
    });
};

export const postEntity = (entity) => (dispatch) => {
  entity.id = undefined;
  console.log("posting entity" + entity);
  postJson({ body: entity, url })
    .then((json) => {
      dispatch({
        type: POST_ENTITY,
        payload: json,
      });
    });
};

export const putEntity = (entity) => (dispatch) => {
  const id = entity.id;
  entity = JSON.stringify(entity);
  console.log("updating entity" + entity);
  putJson({body: entity, url: `${url}/${id}`})
    .then((json) => {
      dispatch({
        type: PUT_ENTITY,
        payload: json,
      });
    });
};

export const clearEntity = () => ({
  type: CLEAR_ENTITY,
  payload: {},
});
