#!/bin/sh

rm -r docs/*
javadoc -public -sourcepath src -d docs -windowtitle 'Baasday Android SDK' -docencoding utf-8 -charset utf-8 com.baasday
