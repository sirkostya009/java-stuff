import Button from "components/Button";
import * as PAGES from "constants/pages";
import {deleteEntity} from "../actions";
import {useState} from "react";

function EntityRow({ id, name, dispatch }) {
  const [shouldShowButtons, setShouldShow] = useState(false);

  const toggleButtons = () => setTimeout(
      () => setShouldShow((state) => !state),
      shouldShowButtons ? 1000 : 0
  );

  return (
      <tr key={id} onMouseEnter={toggleButtons} onMouseLeave={toggleButtons} style={{height: 40}}>
        <td>{id}</td>
        <td>{name}</td>
        {shouldShowButtons && (
            <td>
              <Button href={`/${PAGES.EDIT}?id=${id}`}>Edit</Button>
            </td>
        )}
        {shouldShowButtons && (
            <td>
              <Button onClick={() => dispatch(deleteEntity(id))}>Delete</Button>
            </td>
        )}
      </tr>
  );
}

export default EntityRow;
