import {
  getToken,
} from 'token';

const getHeaders = () => ({
  Accept: 'application/json',
  Authorization: `Bearer ${getToken()}`,
  'Content-Type': 'application/json',
});

const fetchGet = ({ params = {}, url }) => {
  url = new URL(url);
  url.search = new URLSearchParams(params).toString();
  return fetch(
    url,
    {
      headers: getHeaders(),
      method: 'GET',
    }
  );
};

const fetchPost = ({ body, params = {}, url }) => {
  url = new URL(url);
  url.search = new URLSearchParams(params).toString();

  return fetch(
    url,
    {
      body: JSON.stringify(body),
      headers: getHeaders(),
      method: 'POST',
    }
  );
};

export const getJson = ({
  params,
  url,
}) => {
  return fetchGet({
    params,
    url,
  }).then((response) => {
    if (response.ok) {
      return response.json();
    }
    throw response;
  });
};

export const postJson = ({
  body,
  params,
  url,
}) => {
  return fetchPost({
    body,
    params,
    url,
  }).then((response) => {
    if (response.ok) {
      return response.json();
    }
    throw response;
  });
};

export const deleteId = (url) => {
  return fetch(url, {method: 'DELETE', headers: getHeaders()})
    .then((response) => {
      if (response.ok || response.statusCode === 204) {
        return response.json();
      }
      throw response;
    });
};

export const putJson = ({body, url}) => {
  return fetch(url, {method: 'PUT', headers: getHeaders(), body})
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      throw response;
    });
};
