const webpack = require('webpack');
const path = require('path');

module.exports = {
    entry: "./index.js",
    output: {
        path: path.resolve(__dirname, '../resources/static/assets/scripts'),
        filename: "qxcmp.js"
    },

    devtool: "source-map",

    plugins: [
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery"
        })
    ],

    module: {
        loaders: [
            {
                test: /\.js|jsx$/,
                loader: "babel-loader",
                query: {presets: ['es2015']}
            }, {
                test: /\.css$/,
                loader: 'style-loader!css-loader'
            }, {
                test: /\.scss$/,
                loader: 'style-loader!css-loader!sass-loader'
            }, {
                test: /\.(png|jpg|gif)$/,
                loader: 'url-loader',
                query: {
                    limit: 8192,
                    publicPath: "/assets/scripts/",
                    name: "[name].[ext]"
                }
            }, {
                test: /\.(ttf|eot|svg|woff(2)?)(\?[a-z0-9=&.]+)?$/,
                loader: 'file-loader',
                query: {
                    publicPath: "/assets/scripts/",
                    name: "[name].[ext]"
                }
            }
        ]
    }
};