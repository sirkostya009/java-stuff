import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {deleteEntity, fetchEntities} from "../actions";
import Button from "components/Button";
import * as PAGES from 'constants/pages';

function Entities() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchEntities());
  }, []);

  const { entities } = useSelector((store) => store.entityReducer);

  const [state, setState] = useState(entities);

  useEffect(() => { // another crutch.
    if (!entities.isEmpty) setState(entities);
  }, [entities]);

  return (
      <table>
        <thead>
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th><Button href={`/${PAGES.EDIT}`}>Create</Button></th>
        </tr>
        </thead>
        <tbody>
        {!state.isEmpty && state.map(({id, name}) => (
            <tr key={id}>
              <td>{id}</td>
              <td>{name}</td>
              <td>
                <Button href={`/${PAGES.EDIT}?id=${id}`}>Edit</Button>
              </td>
              <td>
                <Button onClick={() => dispatch(deleteEntity(id))}>Delete</Button>
              </td>
            </tr>
        ))}
        </tbody>
      </table>
  );
}

export default Entities;
