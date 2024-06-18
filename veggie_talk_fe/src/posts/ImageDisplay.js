import React from 'react';
import PropTypes from 'prop-types';

const ImageDisplay = ({ fileNames = [] }) => {
    const bucketUrl = "https://veggietalkbucket.s3.eu-north-1.amazonaws.com/"; // Replace with your bucket's base URL

    if (!fileNames || !Array.isArray(fileNames)) {
        return <p>No images to display.</p>;
    }

    return (
        <div className="image-container">
            {fileNames.map((fileName, index) => (
                <img
                    key={index}
                    src={`${bucketUrl}${fileName}`}
                    alt={`Image ${index + 1}`}
                    style={{ maxWidth: '100%', height: 'auto', marginBottom: '10px' }} // Example inline styles
                    onError={(e) => { e.target.src = '/path/to/placeholder/image.png'; }} // Placeholder image on error
                />
            ))}
        </div>
    );
};

ImageDisplay.propTypes = {
    fileNames: PropTypes.arrayOf(PropTypes.string)
};

ImageDisplay.defaultProps = {
    fileNames: []
};

export default ImageDisplay;
