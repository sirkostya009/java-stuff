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
      dispatch(fetchEntity(id));
    else
      dispatch(clearEntity());
  }, []);

  const { entity } = useSelector((store) => store.editReducer);

  const [name, setName] = useState(entity?.name);

  const onSave = () => {
    dispatch((id ? putEntity : postEntity)({ id, name }));
  };

  return (
      <div style={{display: 'flex', flexDirection: 'column', width: 200}}>
        {(id && `new name for ${id} `) || 'create name '}
        <TextField value={name} onChange={({target}) => setName(target.value)} />
        <div style={{display: 'flex', flexDirection: 'row'}}>
          <Link to={`/${PAGES.ENTITIES}`}>Cancel</Link>
          <Button onClick={onSave}>Save</Button>
        </div>
      </div>
  );
}

export default Edit;
