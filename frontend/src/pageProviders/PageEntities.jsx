import React from 'react';
import PageAccessValidator from 'components/PageAccessValidator';
import EntitiesPage from 'pages/Entities';
import PageContainer from 'components/PageContainer';

const PageEntities = () => (
  <PageAccessValidator>
    <PageContainer>
      <EntitiesPage />
    </PageContainer>
  </PageAccessValidator>
);

export default PageEntities;
