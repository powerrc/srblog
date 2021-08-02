import React from 'react'
import { Dimmer, Loader, Segment } from 'semantic-ui-react'
const LoadingSpin = () => (
    <Segment>
      <Dimmer active page>
        <Loader>Loading</Loader>
      </Dimmer>
    </Segment>
  )
export default LoadingSpin;