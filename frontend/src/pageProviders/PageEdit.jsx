import React from 'react';
import PageAccessValidator from 'components/PageAccessValidator';
import EditPage from 'pages/Edit';
import PageContainer from 'components/PageContainer';

const PageEdit = () => (
    <PageAccessValidator>
      <PageContainer>
        <EditPage />
      </PageContainer>
    </PageAccessValidator>
);

export default PageEdit;
