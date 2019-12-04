import React, {Component} from 'react';
import GooglePlacesAutocomplete from 'react-google-places-autocomplete';

class PlacesAutoComplete extends Component {
    constructor(props) {
        super(props);
    }


    render() {
        return (
            <div>
                <GooglePlacesAutocomplete
                    onSelect={function (description) {
                        console.log("Address: " + description);
                    }
                    }
                    autocompletionRequest={{
                        componentRestrictions: {
                            country: "no",
                        }
                    }}

                />
            </div>
        );
    }
}

export default PlacesAutoComplete;
