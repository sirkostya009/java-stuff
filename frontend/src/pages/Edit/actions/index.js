import {getJson, postJson, putJson} from "requests";
import {FETCH_ENTITY, POST_ENTITY, PUT_ENTITY} from "../constants/actionTypes";
import config from "config";

const {
  BASE_URL,
  ENTITY_SERVICE
} = config;

const url = `${BASE_URL}${ENTITY_SERVICE}`;

export const fetchEntity = (id) => (dispatch) => {
  if (id === null)
    dispatch({
      type: FETCH_ENTITY,
      payload: {},
    });
  else
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
          payload: { id, name: 'cool' }
        });
      });
};

export const postEntity = (entity) => (dispatch) => {
  entity.id = undefined;
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
  putJson({body: entity, url: `${url}/${id}`})
    .then((json) => {
      dispatch({
        type: PUT_ENTITY,
        payload: json,
      });
    });
};
