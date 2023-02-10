import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {clearEntity, fetchEntity, postEntity, putEntity} from "../actions";
import TextField from "components/TextField";
import Link from "components/Link";
import * as PAGES from "constants/pages";
import {Button} from "@material-ui/core";
import {useLocation} from "react-router-dom";

function Edit() {
  const dispatch = useDispatch();

  const location = useLocation();
  const params = new URLSearchParams(location.search);
  const id = params.get('id');

  useEffect(() => {
    if (id !== null)
      dispatch(fetchEntity(id))
    else dispatch(clearEntity())
  }, [id]);

  const { entity } = useSelector((store) => store.editReducer);

  const [idState, setId] = useState(entity?.id);
  const [nameState, setName] = useState(entity?.name);

  const idField = <TextField value={idState} onChange={({target}) => setId(target.value)} />
  const nameField = <TextField value={nameState} onChange={({target}) => setName(target.value)} />

  const makeEntity = () => ({
    id: idState,
    name: nameState,
  });

  const finalizeButtonAction = id ? putEntity : postEntity;

  return (
      <div style={{display: 'flex', flexDirection: 'column', width: 150}}>
        {id !== null && 'id:'}
        {id !== null && idField}
        name: {nameField}
        <div style={{display: 'flex', flexDirection: 'row'}}>
          <Link to={`/${PAGES.ENTITIES}`}>Cancel</Link>
          <Button onClick={() => dispatch(finalizeButtonAction(makeEntity()))}>Save</Button>
        </div>
      </div>
  );
}

export default Edit;
