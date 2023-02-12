import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {fetchEntities} from "../actions";
import Button from "components/Button";
import * as PAGES from 'constants/pages';
import EntityRow from "./EntityRow";

function Entities() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchEntities());
  }, []);

  const { entities } = useSelector((store) => store.entityReducer);

  const [state, setState] = useState(entities);

  useEffect(() => { // another crutch.
    if (entities.length > 0) setState(entities);
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
        {state.map(({id, name}) => <EntityRow id={id} name={name} dispatch={dispatch} />)}
        </tbody>
      </table>
  );
}

export default Entities;
