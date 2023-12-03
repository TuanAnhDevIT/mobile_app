package com.sunit.mobileapp.Model;

public class MapOptions {

    private Options options;

    public Options getOptions() {
        return options;
    }

    public static class Options {
        private double[] center;
        private double[] bounds;
        private int zoom;
        private int minZoom;
        private int maxZoom;
        private boolean boxZoom;
        private String geocodeUrl;

        public double[] getCenter() {
            return center;
        }

        public double[] getBounds() {
            return bounds;
        }

        public int getZoom() {
            return zoom;
        }

        public int getMinZoom() {
            return minZoom;
        }

        public int getMaxZoom() {
            return maxZoom;
        }

        public boolean isBoxZoom() {
            return boxZoom;
        }

        public String getGeocodeUrl() {
            return geocodeUrl;
        }
    }
}
