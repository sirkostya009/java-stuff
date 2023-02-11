import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {fetchEntity, postEntity, putEntity} from "../actions";
import TextField from "components/TextField";
import * as PAGES from "constants/pages";
import {Button} from "@material-ui/core";
import useChangePage from "hooks/useChangePage";
import useLocationSearch from "hooks/useLocationSearch";

function Edit() {
  const dispatch = useDispatch();

  const { id } = useLocationSearch();

  const changePage = useChangePage();

  useEffect(() => {
    dispatch(fetchEntity(id));
  }, [id]);

  const { entity } = useSelector((store) => store.editReducer);

  const [name, setName] = useState('');

  useEffect(() => { // this is a crutch, but I don't know how to resolve this issue the right way.
    if (entity) setName(entity?.name);
  }, [entity]);

  return (
      <div>
        {(id && `new name for ${id} `) || 'create name '}
        <div>
          <TextField value={name} onChange={({target}) => setName(target.value)} />
          <div>
            <Button href={`/${PAGES.ENTITIES}`}>Cancel</Button>
            <Button
                onClick={() => {
                  dispatch((id ? putEntity : postEntity)({id, name}));
                  changePage({path: `/${PAGES.ENTITIES}`});
                }}
            >
              Save
            </Button>
          </div>
        </div>
      </div>
  );
}

export default Edit;
