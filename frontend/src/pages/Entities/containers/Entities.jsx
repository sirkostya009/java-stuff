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
      <>
        <Link to={`/${PAGES.EDIT}`}>Create</Link>
        <table>
          <thead>
          <tr>
            <td>Id</td>
            <td>Name</td>
          </tr>
          </thead>
          <tbody>
          {entities && entities.map(({id, name}) => (
              <tr>
                <td>{id}</td>
                <td>{name}</td>
                <td>
                  <Link to={`/${PAGES.EDIT}?id=${id}`}>Edit</Link>
                  <Button to={() => dispatch(deleteEntity(id))}>Delete</Button>
                </td>
              </tr>
          ))}
          </tbody>
        </table>
      </>
  );
}

export default Entities;
