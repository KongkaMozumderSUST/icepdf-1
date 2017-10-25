/*
 * Copyright 2006-2017 ICEsoft Technologies Canada Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS
 * IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.icepdf.ri.common.views.annotations.summary;

import org.icepdf.core.pobjects.annotations.MarkupAnnotation;
import org.icepdf.core.pobjects.annotations.PopupAnnotation;
import org.icepdf.ri.common.DragDropColorList;
import org.icepdf.ri.common.views.AbstractPageViewComponent;
import org.icepdf.ri.common.views.Controller;
import org.icepdf.ri.common.views.annotations.PopupAnnotationComponent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 */
public class ColorLabelPanel extends JPanel {

    private Controller controller;
    private DragDropColorList.ColorLabel colorLabel;
    private DraggableAnnotationPanel draggableAnnotationPanel;

    public ColorLabelPanel(Controller controller, DragDropColorList.ColorLabel colorLabel) {
        super();
        this.colorLabel = colorLabel;
        this.controller = controller;

        // setup the gui
        setLayout(new BorderLayout());
        if (colorLabel != null) {
            add(new JLabel("<html><h3>" + colorLabel.getLabel() + "</h3></html>", JLabel.CENTER), BorderLayout.NORTH);
        }
        draggableAnnotationPanel = new DraggableAnnotationPanel();
        add(draggableAnnotationPanel, BorderLayout.CENTER);
    }

    public int getNumberOfComponents() {
        return draggableAnnotationPanel.getComponentCount();
    }

    public void addAnnotation(MarkupAnnotation markupAnnotation) {
        PopupAnnotation popupAnnotation = markupAnnotation.getPopupAnnotation();
        if (popupAnnotation != null) {
            List<AbstractPageViewComponent> pageComponents =
                    controller.getDocumentViewController().getDocumentViewModel().getPageComponents();
            int pageIndex = markupAnnotation.getPageIndex();
            if (pageIndex >= 0) {
                AnnotationSummaryBox popupAnnotationComponent =
                        new AnnotationSummaryBox(popupAnnotation,
                                controller.getDocumentViewController(), pageComponents.get(pageIndex));
                popupAnnotationComponent.setVisible(true);
                popupAnnotationComponent.removeMouseListeners();
                draggableAnnotationPanel.add(popupAnnotationComponent);
            }
        }
    }

    public void addAnnotation(PopupAnnotationComponent popupAnnotationComponent) {
        addAnnotation(popupAnnotationComponent.getAnnotation().getParent());
    }

    public void updateAnnotation(MarkupAnnotation markupAnnotation) {
        for (Component component : draggableAnnotationPanel.getComponents()) {
            if (component instanceof AnnotationSummaryBox) {
                AnnotationSummaryBox annotationSummaryBox = (AnnotationSummaryBox) component;
                MarkupAnnotation currentMarkupAnnotation = annotationSummaryBox.getAnnotation().getParent();
                if (markupAnnotation.getPObjectReference().equals(currentMarkupAnnotation.getPObjectReference())) {
                    annotationSummaryBox.refreshPopupText();
                    annotationSummaryBox.repaint();
                    break;
                }
            }
        }
    }

    public void removeAnnotation(MarkupAnnotation markupAnnotation) {
        for (Component component : draggableAnnotationPanel.getComponents()) {
            if (component instanceof AnnotationSummaryBox) {
                AnnotationSummaryBox annotationSummaryBox = (AnnotationSummaryBox) component;
                MarkupAnnotation currentMarkupAnnotation = annotationSummaryBox.getAnnotation().getParent();
                if (markupAnnotation.getPObjectReference().equals(currentMarkupAnnotation.getPObjectReference())) {
                    draggableAnnotationPanel.remove(component);
                    draggableAnnotationPanel.revalidate();
                    draggableAnnotationPanel.repaint();
                    break;
                }
            }
        }
    }


    public DragDropColorList.ColorLabel getColorLabel() {
        return colorLabel;
    }
}
