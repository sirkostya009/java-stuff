import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {deleteEntity, fetchEntities} from "../actions";
import Button from "components/Button";
import Link from "components/Link";
import * as PAGES from 'constants/pages';

function Entities() {
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(fetchEntities());
  }, []);

  const {
    entities
  } = useSelector((store) => store.entityReducer);

  return (
      <table>
        <thead>
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th><Link to={`/${PAGES.EDIT}`}>Create</Link></th>
        </tr>
        </thead>
        <tbody>
        {!entities.isEmpty && entities.map(({id, name}) => (
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
